package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SovereignsOfLostAlara extends CardImpl {

    public SovereignsOfLostAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Exalted
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, you may search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle your library.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new SovereignsOfLostAlaraEffect(), true, true));
    }

    private SovereignsOfLostAlara(final SovereignsOfLostAlara card) {
        super(card);
    }

    @Override
    public SovereignsOfLostAlara copy() {
        return new SovereignsOfLostAlara(this);
    }
}

class SovereignsOfLostAlaraEffect extends OneShotEffect {

    public SovereignsOfLostAlaraEffect() {
        super(Outcome.BoostCreature);
        staticText = "search your library for an Aura card that could enchant that creature, put it onto the battlefield attached to that creature, then shuffle";
    }

    public SovereignsOfLostAlaraEffect(final SovereignsOfLostAlaraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && attackingCreature != null) {
            FilterCard filter = new FilterCard("Aura card that could enchant " + attackingCreature.getName());
            filter.add(SubType.AURA.getPredicate());
            filter.add(new AuraCardCanAttachToPermanentId(attackingCreature.getId()));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            target.setNotTarget(true);
            if (controller.searchLibrary(target, source, game)) {
                if (target.getFirstTarget() != null) {
                    Card aura = game.getCard(target.getFirstTarget());
                    game.getState().setValue("attachTo:" + aura.getId(), attackingCreature);
                    controller.moveCards(aura, Zone.BATTLEFIELD, source, game);
                    attackingCreature.addAttachment(aura.getId(), source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public SovereignsOfLostAlaraEffect copy() {
        return new SovereignsOfLostAlaraEffect(this);
    }
}
