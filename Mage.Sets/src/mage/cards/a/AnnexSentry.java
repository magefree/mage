package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author AhmadYProjects
 */
public final class AnnexSentry extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature an opponent controls with cmc 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN,4));
        filter.add(Predicates.or
                (CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AnnexSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Annex Sentry enters the battlefield, exile target artifact or creature an opponent controls with mana value 3 or less until Annex Sentry leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private AnnexSentry(final AnnexSentry card) {
        super(card);
    }

    @Override
    public AnnexSentry copy() {
        return new AnnexSentry(this);
    }
}
