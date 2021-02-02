
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class SuspensionField extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness 3 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 2));
    }

    public SuspensionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When Suspension Field enters the battlefield, you may exile target creature with toughness 3 or greater until Suspension Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspensionFieldExileEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

    }

    private SuspensionField(final SuspensionField card) {
        super(card);
    }

    @Override
    public SuspensionField copy() {
        return new SuspensionField(this);
    }
}

class SuspensionFieldExileEffect extends OneShotEffect {

    SuspensionFieldExileEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile target creature with toughness 3 or greater until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    SuspensionFieldExileEffect(final SuspensionFieldExileEffect effect) {
        super(effect);
    }

    @Override
    public SuspensionFieldExileEffect copy() {
        return new SuspensionFieldExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        // If Suspension Field leaves the battlefield before its triggered ability resolves, the target won't be exiled.
        if (sourcePermanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), sourcePermanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
