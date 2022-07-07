# Creating a new theme for XMage

## Introduction

Themes are a feature in XMage that allows the user to change the appearance of the XMage client. Users can choose from a list of themes located on the Themes tab of the Preferences dialog. Once a new theme is chosen and saved, the XMage client must be restarted for the changes to take effect.

The list of themes is loaded from the directories/folders or zip files located within the `/themes` subdirectory. Each directory/folder or zip file must contain a file named `theme.xml` that is properly formatted. The following guide will walk you through how to create your own custom theme.

<b>Content:</b>
- [Creating the Theme File](#creating-the-theme-file)
- [Configuring Theme Attributes](#configuring-theme-attributes)
- [Configuring Theme Colors](#configuring-theme-colors)
- [Adding Theme Defaults](#adding-theme-defaults)
- [Adding Theme Resources](#adding-theme-resources)
- [Additional Information](#additional-information)

## <a name="creating-the-theme-file"></a>Creating the Theme File

The first step is to create a new folder within the `/themes` subdirectory. It is best practice to name this folder the same as your theme name. Create a new file within this folder and name it `theme.xml`. Add the following text to this file and replace `Theme Name Here` with the <b>unique</b> name of your theme:

```
<?xml version="1.0" encoding="UTF-8"?>
<themeConfig>
    <theme
        name="Theme Name Here"
    />
</themeConfig>
```

This is the most basic theme configuration possible and will allow you to select it from the themes list within the XMage client, though it will look identical to the Default theme.

## <a name="configuring-the-theme-attributes"></a>Configuring Theme Attributes

There are several more attributes that can be added to the `<theme/>` element to change the look of your theme.

- `resourcesParentTheme="FlatLaf Dark"`
  - <i>(Optional)</i> This attribute will allow your theme to use the `<themeResources>` defined in the theme with the given name. Any resources defined in your theme will be given priority. Any resources that are not defined in your theme or the parent's theme will use the Default theme's resources. Be careful not to create a cyclical dependency (e.g. Your theme relies on its parent theme, while the parent theme relies on your theme) as that will cause one or both themes to not load. The themes `FlatLaf Dark` and `FlatLaf Light` contain a set of Material icons that can be used as a resource base for a new theme by using this attribute.<br>
  <b>Default:</b> `null`
- `lafClassPath="com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme"`
  - <i>(Optional)</i> This attribute defines the classpath of the Look and Feel the client will use to change every aspect of the visual design, from background colors to button shapes and everything in-between. The FlatLaf plugin provides a bunch of premade themes to choose from which can be found on the [FlatLaf GitHub page](https://github.com/JFormDesigner/FlatLaf).<br>
  <b>Default:</b> `javax.swing.plaf.nimbus.NimbusLookAndFeel`
- `dark="true"`
  - <i>(Optional)</i> This attribute hints that this is a dark mode theme which will change some of the resources displayed to be more visible on a darker background. See [Additional Information](#additional-information) for a full list of affected resources.<br>
  <b>Default:</b> `false`
- `showBackground="false"`
  - <i>(Optional)</i> This attribute defines whether the `/background/background.png` resource should be displayed.<br>
  <b>Default:</b> `true`
- `showLoginBackground="false"`
  - <i>(Optional)</i> This attribute defines whether the `/background/login-background.png` resource should be displayed on the landing screen. Please note that this resource doesn't exist by default and must be added in the `<themeResources>` section to be displayed if this attribute is true.<br>
  <b>Default:</b> `false`
- `showBattleBackground="true"`
  - <i>(Optional)</i> This attribute defines whether the `/background/battle-background.png` resource should be displayed on the in-game screen.<br>
  <b>Default:</b> `true`
- `shortcutsVisibleForSkipButtons="true"`
  - <i>(Optional)</i> This attribute defines whether the keyboard shortcuts (e.g. F2, F3) should be displayed on the in-game skip buttons. If you are replacing the resources for these buttons, this text may interfere with your new button design.<br>
  <b>Default:</b> `true`

## <a name="configuring-theme-colors"></a>Configuring Theme Colors

There are several colors that are not always changed by the Look and Feel. These can be configured using the optional `<themeColors/>` element. Add this element below the `<theme/>` element like in the following example:

```
<?xml version="1.0" encoding="UTF-8"?>
<themeConfig>
    <theme
        name="Theme Name Here"
    />
    <themeColors
        gameEndTextBackgroundColor="rgba(36, 37, 38, 240)"
    />
</themeConfig>
```

All of the attributes in the `<themeColors>` element have colors as their value. Colors can be written in a few formats:
- Hexadecimal (e.g. `#000000`)
- RGB with integer numbers ranging from 0-255 (e.g. `rgb(0, 127, 255)`)
- RGBA with integer numbers ranging from 0-255 (e.g. `rgba(10, 20, 30, 255)`)
- Any color name defined by `java.awt.Color` (e.g. `red`)

These are the available attributes in the `<themeColors>` element.

- `mageToolbarBackgroundColor="#222222"`
  - <i>(Optional)</i> This attribute sets the background color of the toolbar located at the top of the XMage client.<br>
  <b>Default:</b> `null`
- `gameEndTextBackgroundColor="rgba(36, 37, 38, 240)"`
  - <i>(Optional)</i> This attribute sets the background color of the text box in the popup window displayed after a game is finished.<br>
  <b>Default:</b> `rgba(240, 240, 240, 140)` or `rgba(36, 37, 38, 240)` if dark mode
- `cardTooltipBackgroundColor="rgb(60, 63, 65)"`
  - <i>(Optional)</i> This attribute sets the background color of the card hover tooltip.<br>
  <b>Default:</b> `rgb(204, 204, 204)` or `rgb(60, 63, 65)` if dark mode
- `playerPanelInactiveBackgroundColor="rgba(200, 200, 180, 200)"`
  - <i>(Optional)</i> This attribute sets the background color of the in-game player panel for players that are inactive.<br>
  <b>Default:</b> `rgba(200, 200, 180, 200)`
- `playerPanelActiveBackgroundColor="rgba(200, 255, 200, 200)"`
  - <i>(Optional)</i> This attribute sets the background color of the in-game player panel for players that are active.<br>
  <b>Default:</b> `rgba(200, 255, 200, 200)`
- `playerPanelDeadBackgroundColor="rgba(131, 94, 83, 200)"`
  - <i>(Optional)</i> This attribute sets the background color of the in-game player panel for players that are dead.<br>
  <b>Default:</b> `rgba(131, 94, 83, 200)`
- `cardIconsFillColor="rgb(169, 176, 190)"`
  - <i>(Optional)</i> This attribute sets the fill color for icons that are displayed on cards.<br>
  <b>Default:</b> `rgb(169, 176, 190)`
- `cardIconsStrokeColor="black"`
  - <i>(Optional)</i> This attribute sets the stroke/outline color for icons that are displayed on cards.<br>
  <b>Default:</b> `black`
- `cardIconsTextColor="rgb(51, 98, 140)"`
  - <i>(Optional)</i> This attribute sets the text color for icons that are displayed on cards.<br>
  <b>Default:</b> `rgb(51, 98, 140)`
- `textOnBackgroundTextColor="white"`
  - <i>(Optional)</i> This attribute sets the text color for labels that are rendered directly on top of a background image. This only applies if the `<game>` attributes `showBackground` or `showLoginBackground` are true.<br>
  <b>Default:</b> `white`

## <a name="adding-theme-defaults"></a>Adding Theme Defaults

Theme defaults are a powerful way to customize nearly every attribute defined in the Look and Feel. Some keys are specific to certain Look and Feel presets, see [Additional Information](#additional-information) for resources on applicable keys.

The `<themeDefaults>` element can take any number of `<default/>` elements as children. Add this element below the `<theme/>` element (or the `<themeColors/>` element if present) like in the following example:

```
<?xml version="1.0" encoding="UTF-8"?>
<themeConfig>
    <theme
        name="Theme Name Here"
    />
    <themeDefaults>
        <default key="JList.background" color="#800850" />
        <default key="JComponent.arrowType" string="chevron" />
        <default key="JButton.arc" number="999" />
        <default key="JComponent.roundRect" boolean="true" />
    </themeDefaults>
</themeConfig>
```

Each `<default/>` element has the following attributes:

- `key="JList.background"`
  - <i>(Required)</i> This attribute defines the key property you want to alter.
- <b>One-and-only-one of the following:</b>
- `color="rgb(12, 34, 56)"`
  - <i>(Optional)</i> This attribute can be used to set the color value of the property defined by the key.
- `string="chevron"`
  - <i>(Optional)</i> This attribute can be used to set the string value of the property defined by the key.
- `number="999"`
  - <i>(Optional)</i> This attribute can be used to set the number value of the property defined by the key. Numbers can be integer or decimal, negative or positive.
- `boolean="true"`
  - <i>(Optional)</i> This attribute can be used to set the boolean value of the property defined by the key. Can only be `true` or `false`.

## <a name="adding-theme-resources"></a>Adding Theme Resources

Theme resources can be used to override any image resource in the XMage client with a custom image. Custom resources must be located inside your theme folder (where your `theme.xml` file is). It is best practice to follow the same file structure as the default XMage Client resources (e.g. You are overriding `/background/background.png`, so you would create a folder named `background` inside your theme folder and put your new background image inside that new folder). Resources <b>are not</b> required to have the same name as the resource they are overriding.

The `<themeResources>` element can take any number of `<resource/>` elements as children. Add this element below the `<theme/>` element (or the `<themeColors/>` or `<themeDefaults>` element if present) like in the following example:

```
<?xml version="1.0" encoding="UTF-8"?>
<themeConfig>
    <theme
        name="Theme Name Here"
    />
    <themeResources>
        <resource name="/menu/about.png" relPath="/menu/baseline_info_white_18dp.png" />
        <resource name="/menu/collection.png" relPath="/menu/collection.png" />
    </themeResources>
</themeConfig>
```

Each `<resource/>` element has the following attributes:

- `name="/menu/about.png"`
  - <i>(Required)</i> This attribute defines the path of the default resource you want to override with a custom resource. You can easily find these resource paths located in the XMage sources files in `Mage.Client/src/main/resources/`. All paths <b>must</b> start with `/`. If this theme is dark mode, some dark mode compatible resource paths must be used instead (e.g. `/menu/deck_editor.dark.png`). See [Additional Information](#additional-information) for a full list of affected resources.
- `relPath="/menu/baseline_info_black_18dp.png"`
  - <i>(Required)</i> This attribute defines the relative path of the custom resource to want to be display instead. This resource must be located inside this theme's folder. The filename <b>does not</b> have to be the same as the resource you are overriding, but it may help.

## <a name="additional-information"></a>Additional Information

Themes can also be zip files located in the `/themes` directory so long as the `theme.xml` file is in the root of the zip file.

Some resources have been optimized to look better in dark mode by default. In order to override these resources in a theme with `dark="true"`, you must add the extension `.dark` to the filename (e.g. `/menu/deck_editor.png` would be `/menu/deck_editor.dark.png` in dark mode). This is a list of all resources affected (this list may be expanded):

- `/menu/deck_editor.png`
- `/menu/symbol.png`
- `/buttons/type_land.png`
- `/buttons/type_planeswalker.png`
- `/buttons/search_24.png`
- `/buttons/search_32.png`
- `/buttons/search_64.png`
- `/buttons/search_128.png`
- `/buttons/tourney_new.png`
- `/buttons/deck_pack.png`
- `/buttons/match_new.png`
- `/card/triggered_ability.png`
- `/card/activated_ability.png`
- `/card/copy.png`
- `/game/revealed.png`
- `/game/looked_at.png`
- `/card/token.png`
- `/card/night.png`
- `/card/day.png`
---
<b>External Links:</b>
- FlatLaf Github page: https://github.com/JFormDesigner/FlatLaf
- FlatLaf Default keys and values: https://www.formdev.com/flatlaf/customizing/
- Java Docs on Nimbus Default keys: https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html