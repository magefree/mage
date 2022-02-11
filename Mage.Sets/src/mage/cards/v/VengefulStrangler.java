package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulStrangler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VengefulStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.s.StranglingGrasp.class;

        // Vengeful Strangler can't block.
        this.addAbility(new CantBlockAbility());

        // When Vengeful Strangler dies, return it to the battlefield transformed under your control attached to target creature or planeswalker an opponent controls.
        this.addAbility(new TransformAbility());
        Ability ability = new DiesSourceTriggeredAbility(new VengefulStranglerEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VengefulStrangler(final VengefulStrangler card) {
        super(card);
    }

    @Override
    public VengefulStrangler copy() {
        return new VengefulStrangler(this);
    }
}

class VengefulStranglerEffect extends OneShotEffect {

    VengefulStranglerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control " +
                "attached to target creature or planeswalker an opponent controls";
    }

    private VengefulStranglerEffect(final VengefulStranglerEffect effect) {
        super(effect);
    }

    @Override
    public VengefulStranglerEffect copy() {
        return new VengefulStranglerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null
                || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        UUID secondFaceId = game.getCard(source.getSourceId()).getSecondCardFace().getId();
        game.getState().setValue("attachTo:" + secondFaceId, permanent.getId());
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            permanent.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}

