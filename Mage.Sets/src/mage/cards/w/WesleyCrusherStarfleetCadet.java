package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class WesleyCrusherStarfleetCadet extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 2));
    }

    public WesleyCrusherStarfleetCadet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Wesley Crusher enters, choose two --
        // * Counter target noncreature spell unless its controller pays {2}.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        ability.getModes().setMinModes(2);
        ability.getModes().setMaxModes(2);

        // * Return target creature with mana value 2 or less to its owner's hand.
        ability.addMode(new Mode(new ReturnToHandTargetEffect()).addTarget(new TargetPermanent(filter)));

        // * Target creature can't be blocked this turn.
        ability.addMode(new Mode(new CantBeBlockedTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // * Target player draws two cards, then discards two cards.
        ability.addMode(new Mode(new DrawDiscardTargetEffect(2, 2)).addTarget(new TargetPlayer()));
        this.addAbility(ability);
    }

    private WesleyCrusherStarfleetCadet(final WesleyCrusherStarfleetCadet card) {
        super(card);
    }

    @Override
    public WesleyCrusherStarfleetCadet copy() {
        return new WesleyCrusherStarfleetCadet(this);
    }
}
