package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronsoulEnforcer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("{this} or a commander you control");

    static {
        filter.add(IronsoulEnforcerPredicate.instance);
    }

    public IronsoulEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Ironsoul Enforcer or a commander you control attacks alone, return target artifact card from your graveyard to the battlefield.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), filter, false, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private IronsoulEnforcer(final IronsoulEnforcer card) {
        super(card);
    }

    @Override
    public IronsoulEnforcer copy() {
        return new IronsoulEnforcer(this);
    }
}

enum IronsoulEnforcerPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getId().equals(input.getSourceId())
                || CommanderPredicate.instance.apply(input.getObject(), game);
    }
}