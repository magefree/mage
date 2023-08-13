package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class NivMizzetParun extends CardImpl {

    public NivMizzetParun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}{U}{R}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw a card, Niv-Mizzet, Parun deals 1 damage to any target.
        Ability ability = new DrawCardControllerTriggeredAbility(
                new DamageTargetEffect(1), false
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Whenever a player casts an instant or sorcery spell, you draw a card.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private NivMizzetParun(final NivMizzetParun card) {
        super(card);
    }

    @Override
    public NivMizzetParun copy() {
        return new NivMizzetParun(this);
    }
}
