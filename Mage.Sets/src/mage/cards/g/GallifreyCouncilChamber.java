package mage.cards.g;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GallifreyCouncilChamber extends CardImpl {

    public GallifreyCouncilChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // When Gallifrey Council Chamber enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Time Lord or Alien spell or activate an ability of a Time Lord or Alien.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new GallifreyCouncilChamberManaBuilder()));
    }

    private GallifreyCouncilChamber(final GallifreyCouncilChamber card) {
        super(card);
    }

    @Override
    public GallifreyCouncilChamber copy() {
        return new GallifreyCouncilChamber(this);
    }
}

class GallifreyCouncilChamberManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new GallifreyCouncilChamberConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Time Lord or Alien spell or activate an ability of a Time Lord or Alien";
    }
}

class GallifreyCouncilChamberConditionalMana extends ConditionalMana {

    GallifreyCouncilChamberConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast a Time Lord or Alien spell or activate an ability of a Time Lord or Alien";
        addCondition(GallifreyCouncilChamberManaCondition.instance);
    }
}

enum GallifreyCouncilChamberManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && (object.hasSubtype(SubType.TIME_LORD, game) || object.hasSubtype(SubType.ALIEN, game));
    }
}
