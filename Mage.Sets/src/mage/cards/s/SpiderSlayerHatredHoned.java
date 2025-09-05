package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.RobotFlyingToken;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SpiderSlayerHatredHoned extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a Spider");

    static {
        filter.add(SubType.SPIDER.getPredicate());
    }

    public SpiderSlayerHatredHoned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Spider-Slayer deals damage to a Spider, destroy that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new DestroyTargetEffect(),
                false,
                false,
                true,
                filter
        ));

        // {6}, Exile this card from your graveyard: Create two tapped 1/1 colorless Robot artifact creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new RobotFlyingToken(), 2, true),
                new ManaCostsImpl<>("{6}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SpiderSlayerHatredHoned(final SpiderSlayerHatredHoned card) {
        super(card);
    }

    @Override
    public SpiderSlayerHatredHoned copy() {
        return new SpiderSlayerHatredHoned(this);
    }
}
