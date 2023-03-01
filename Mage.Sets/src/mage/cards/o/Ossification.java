package mage.cards.o;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.constants.*;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author AhmadYProjects
 */
public final class Ossification extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("basic land you control");

    static{
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final FilterPermanent filter2 = new FilterPermanent("creature or planeswalker an opponent controls");
    static{
        filter2.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }


    public Ossification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant basic land you control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Ossification enters the battlefield, exile target creature or planeswalker an opponent controls until Ossification leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter2));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private Ossification(final Ossification card) {
        super(card);
    }

    @Override
    public Ossification copy() {
        return new Ossification(this);
    }
}
