package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KoglaTheTitanApe extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment defending player controls");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.HUMAN);

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    public KoglaTheTitanApe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.APE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // When Kogla, the Titan Ape enters the battlefield, it fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights up to one target creature you don't control"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Whenever Kogla attacks, destroy target artifact or enchantment defending player controls.
        ability = new AttacksTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {1}{G}: Return target Human you control to its owner's hand. Kogla gains indestructible until end of turn.
        ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private KoglaTheTitanApe(final KoglaTheTitanApe card) {
        super(card);
    }

    @Override
    public KoglaTheTitanApe copy() {
        return new KoglaTheTitanApe(this);
    }
}
// it was beauty killed the beast
