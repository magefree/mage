package mage.cards.c;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastleGarenbrig extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.FOREST);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public CastleGarenbrig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Castle Garenbrig enters the battlefield tapped unless you control a Forest.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // {2}{G}{G}, {T}: Add six {G}. Spend this mana only to cast creature spells or activate abilities of creatures.
        Ability ability = new ConditionalColoredManaAbility(
                new ManaCostsImpl<>("{2}{G}{G}"), Mana.GreenMana(6), new CastleGarenbrigManaBuilder()
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleGarenbrig(final CastleGarenbrig card) {
        super(card);
    }

    @Override
    public CastleGarenbrig copy() {
        return new CastleGarenbrig(this);
    }
}

class CastleGarenbrigManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CastleGarenbrigConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells or activate abilities of creatures";
    }
}

class CastleGarenbrigConditionalMana extends ConditionalMana {

    CastleGarenbrigConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast creature spells or activate abilities of creatures";
        addCondition(CastleGarenbrigManaCondition.instance);
    }
}

enum CastleGarenbrigManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null && object.isCreature(game)) {
            return true;
        }
        return false;
    }
}