package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlingGangLieutenant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public SlingGangLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sling-Gang Lieutenant enters the battlefield, create two 1/1 red Goblin creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(), 2)));

        // Sacrifice a Goblin: Target player loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SlingGangLieutenant(final SlingGangLieutenant card) {
        super(card);
    }

    @Override
    public SlingGangLieutenant copy() {
        return new SlingGangLieutenant(this);
    }
}
