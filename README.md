# Xulai Create Tweaker

**Script-driven recipe control for Create.**

Xulai Create Tweaker is an addon for CraftTweaker that brings Create's machine recipes into ZenScript. Pack authors can add, remove and rewrite recipes entirely from script files, without writing or maintaining datapack JSON by hand.

Every recipe type is exposed through its own manager with a consistent shape, so an entire processing chain can be described in one place.

## ✨ Features

- **Full recipe coverage** — every machine processing type Create uses, from Crushing Wheels to Sequenced Assembly.
- **Weighted outputs** — chance-based results are supported directly, and a single recipe can carry several outputs.
- **Fluid support** — fluid ingredients and fluid results are available wherever the machine accepts them.
- **Heat requirements** — recipes can require no heat, heated, or superheated conditions.
- **Processing time** — duration is configurable per recipe and falls back to a sensible default when omitted.
- **Deployer control** — held items can be kept instead of consumed, and deployer results can be computed at crafting time rather than fixed in advance.
- **Sequenced assembly** — multi-step lines with ordered steps, transitional items, loop counts and weighted final outputs.
- **Grid recipes** — mechanical crafting patterns are defined as a grid, with optional horizontal mirroring.
- **Native CraftTweaker integration** — dumps, removal by output, removal by mod and bulk replacement behave as they do for vanilla recipes; a dedicated heat component lets replacement rules target heated recipes only.

## 🔧 Covered recipe types

| Recipe type | Where it is used |
| --- | --- |
| Crushing | Crushing Wheels |
| Milling | Millstone |
| Cutting | Mechanical Saw |
| Pressing | Mechanical Press |
| Mixing | Mechanical Mixer and Basin |
| Compacting | Mechanical Press and Basin |
| Filling | Spout |
| Emptying | Draining a container |
| Splashing | Encased Fan, washing |
| Haunting | Encased Fan, haunting |
| Sand Paper Polishing | Sand Paper |
| Deployer Application | Deployer |
| Item Application | Applying one item to another |
| Mechanical Crafting | Mechanical Crafter |
| Sequenced Assembly | Sequenced Assembly line |

## 📦 Requirements

| Mod | Role |
| --- | --- |
| Create | Supplies the recipes being edited |
| CraftTweaker | Supplies the scripting layer |

Both are required. This mod adds no blocks, items or machines of its own; it only edits recipes.

## 💡 Notes

- Recipes are declared in scripts and take effect through CraftTweaker's normal recipe loading, with no datapack editing involved.
- Every exposed type is documented through CraftTweaker's standard documentation system.
- Reloading scripts applies recipe changes without restarting the game.

## 🐛 Feedback

Bug reports and suggestions are welcome in the issue tracker. Please include the script that triggers the problem and the related log lines.

---

# Xulai Create Tweaker 中文介绍

**用脚本掌控机械动力的配方。**

Xulai Create Tweaker 是 CraftTweaker 的附属模组，将机械动力的机器配方接入 ZenScript。整合包作者可以完全通过脚本文件添加、删除与改写配方，无需手写或维护数据包 JSON。

每类配方都由独立的管理器提供，接口形态保持一致，整条加工链可以在同一处描述。

## ✨ 功能

- **配方类型完整** — 覆盖机械动力用于机器加工的全部配方类型，从粉碎轮到序列组装。
- **带权重的产出** — 直接支持按概率产出的结果，单条配方也可包含多个产出。
- **流体支持** — 在机器允许的环节提供流体原料与流体产物。
- **加热条件** — 配方可要求无加热、加热或超级加热。
- **处理时间** — 每条配方可单独设定耗时，省略时使用合理的默认值。
- **机械手控制** — 手持物可设定为不消耗，产出也可在合成时计算，而非预先固定。
- **序列组装** — 支持多步骤流水线，包括有序步骤、过渡物品、循环次数与带权重的最终产出。
- **网格配方** — 动力合成配方以网格形式定义，可选择接受水平镜像。
- **与 CraftTweaker 原生集成** — 配方导出、按产出删除、按模组删除与批量替换均与原生配方表现一致；另有独立的加热组件，使替换规则可以只作用于需要加热的配方。

## 🔧 覆盖的配方类型

| 配方类型 | 使用场景 |
| --- | --- |
| 粉碎 | 粉碎轮 |
| 研磨 | 磨石 |
| 切割 | 机械锯 |
| 压制 | 机械压床 |
| 混合 | 机械搅拌器与工作盆 |
| 压实 | 机械压床与工作盆 |
| 灌注 | 注液器 |
| 排空 | 从容器中排出液体 |
| 洗涤 | 鼓风机洗涤 |
| 缠魂 | 鼓风机缠魂 |
| 打磨 | 砂纸 |
| 机械手应用 | 机械手 |
| 物品应用 | 将一件物品施加到另一件物品上 |
| 动力合成 | 动力合成器 |
| 序列组装 | 序列组装流水线 |

## 📦 前置要求

| 模组 | 作用 |
| --- | --- |
| 机械动力 | 提供被编辑的配方 |
| CraftTweaker | 提供脚本层 |

两者均为必需。本模组不添加任何方块、物品或机器，仅用于编辑配方。

## 💡 说明

- 配方在脚本中声明，经由 CraftTweaker 常规的配方加载流程生效，无需改动数据包。
- 全部公开类型均通过 CraftTweaker 标准文档系统提供说明。
- 重载脚本即可应用配方改动，无需重启游戏。

## 🐛 反馈

问题与建议请提交至问题追踪器。提交时请附上触发问题的脚本与相关日志。
