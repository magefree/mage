package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessVinestalk extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RestlessVinestalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Fortress enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // {3}{G}{U}: Until end of turn, Restless Vinestalk becomes a 5/5 green and blue Plant creature with trample. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(5, 5, "5/5 green and blue Plant creature with trample")
                        .withColor("GU").withSubType(SubType.PLANT)
                        .withAbility(TrampleAbility.getInstance()),
                CardType.LAND, Duration.EndOfTurn, true
        ), new ManaCostsImpl<>("{3}{G}{U}")));

        // Whenever Restless Vinestalk attacks, up to one other target creature has base power and toughness 3/3 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new SetBasePowerToughnessTargetEffect(3, 3, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private RestlessVinestalk(final RestlessVinestalk card) {
        super(card);
    }

    @Override
    public RestlessVinestalk copy() {
        return new RestlessVinestalk(this);
    }
}
