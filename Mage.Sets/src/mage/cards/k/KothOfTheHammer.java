
package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.command.emblems.KothOfTheHammerEmblem;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class KothOfTheHammer extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent(SubType.MOUNTAIN, "Mountain");
    static final FilterPermanent filterCount = new FilterControlledPermanent("Mountain you control");

    public KothOfTheHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOTH);

        this.setStartingLoyalty(3);

        // +1: Untap target Mountain. It becomes a 4/4 red Elemental creature until end of turn. It's still a land.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 1);
        ability.addEffect(new BecomesCreatureTargetEffect(new KothOfTheHammerToken(), false, true, Duration.EndOfTurn).withTargetDescription("It"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -2: Add {R} for each Mountain you control.
        this.addAbility(new LoyaltyAbility(new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filterCount)), -2));

        // -5: You get an emblem with "Mountains you control have '{T}: This land deals 1 damage to any target.'
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KothOfTheHammerEmblem()), -5));
    }

    private KothOfTheHammer(final KothOfTheHammer card) {
        super(card);
    }

    @Override
    public KothOfTheHammer copy() {
        return new KothOfTheHammer(this);
    }
}

class KothOfTheHammerToken extends TokenImpl {

    public KothOfTheHammerToken() {
        super("Elemental", "4/4 red Elemental creature");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }

    private KothOfTheHammerToken(final KothOfTheHammerToken token) {
        super(token);
    }

    public KothOfTheHammerToken copy() {
        return new KothOfTheHammerToken(this);
    }
}
