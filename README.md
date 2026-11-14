# Xulai Create Tweaker

Script-driven recipe control for Create.

Xulai Create Tweaker is an addon for [CraftTweaker](https://github.com/CraftTweaker/CraftTweaker) that exposes Create's machine recipes to ZenScript. Pack authors can add, remove and rewrite machine recipes from script files alone, without writing or maintaining datapack JSON by hand.

## Features

- **Full recipe coverage** — every machine processing type Create uses, from Crushing Wheels to Sequenced Assembly.
- **Weighted outputs** — chance-based results are supported directly, and a single recipe can carry several outputs.
- **Fluid support** — fluid ingredients and fluid results are available wherever the machine accepts them.
- **Heat requirements** — recipes can require no heat, heated, or superheated conditions.
- **Processing time** — duration is configurable per recipe and falls back to a sensible default when omitted.
- **Deployer control** — held items can be kept instead of consumed, and deployer results can be computed at crafting time rather than fixed in advance.
- **Sequenced assembly** — multi-step lines with ordered steps, transitional items, loop counts and weighted final outputs.
- **Grid recipes** — mechanical crafting patterns are defined as a grid, with optional horizontal mirroring.
- **Native CraftTweaker integration** — dumps, removal by output, removal by mod and bulk replacement behave as they do for vanilla recipes; a dedicated heat component lets replacement rules target heated recipes only.

## Covered recipe types

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

## Requirements

- **Create** — supplies the recipes being edited.
- **CraftTweaker** — supplies the scripting layer.

Both are required. This mod adds no blocks, items or machines of its own; it only edits recipes.

## Documentation

The full store description is available in [CURSEFORGE.md](CURSEFORGE.md). Every exposed type is also documented through CraftTweaker's standard documentation system.

## License

All Rights Reserved. Template files originating from the NeoForged MDK remain under the terms in [TEMPLATE_LICENSE.txt](TEMPLATE_LICENSE.txt).
