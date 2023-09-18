
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class IridescentDrake extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Aura from a graveyard");
    
    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public IridescentDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Iridescent Drake enters the battlefield, put target Aura card from a graveyard onto the battlefield under your control attached to Iridescent Drake.
        Ability ability = new EntersBattlefieldTriggeredAbility(new IridescentDrakeEffect());
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);
    }

    private IridescentDrake(final IridescentDrake card) {
        super(card);
    }

    @Override
    public IridescentDrake copy() {
        return new IridescentDrake(this);
    }
}

class IridescentDrakeEffect extends OneShotEffect {

    public IridescentDrakeEffect() {
        super(Outcome.Benefit);
        this.staticText = "put target Aura card from a graveyard onto the battlefield under your control attached to {this}";
    }

    private IridescentDrakeEffect(final IridescentDrakeEffect effect) {
        super(effect);
    }

    @Override
    public IridescentDrakeEffect copy() {
        return new IridescentDrakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        Card targetAuraCard = game.getCard(source.getFirstTarget());
        if (controller != null
                && permanent != null
                && controller.canRespond()
                && targetAuraCard != null
                && new AuraCardCanAttachToPermanentId(permanent.getId()).apply(targetAuraCard, game)) {
            Target target = targetAuraCard.getSpellAbility().getTargets().get(0);
            if (target != null) {
                game.getState().setValue("attachTo:" + targetAuraCard.getId(), permanent);
                controller.moveCards(targetAuraCard, Zone.BATTLEFIELD, source, game);
                return permanent.addAttachment(targetAuraCard.getId(), source, game);
            }
        }
        return false;
    }
}
