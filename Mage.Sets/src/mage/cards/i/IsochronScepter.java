package mage.cards.i;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public final class IsochronScepter extends CardImpl {

    public IsochronScepter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Imprint - When Isochron Scepter enters the battlefield, you may exile an instant card with converted mana cost 2 or less from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new IsochronScepterImprintEffect(),true)
                .setAbilityWord(AbilityWord.IMPRINT)
        );

        // {2}, {T}: You may copy the exiled card. If you do, you may cast the copy without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new IsochronScepterCopyEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private IsochronScepter(final IsochronScepter card) {
        super(card);
    }

    @Override
    public IsochronScepter copy() {
        return new IsochronScepter(this);
    }
}

class IsochronScepterImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant card with "
            + "mana value 2 or less from your hand");

    static {
        filter.add(CardType.INSTANT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public IsochronScepterImprintEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile an instant card with mana value 2 or less from your hand";
    }

    public IsochronScepterImprintEffect(IsochronScepterImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            if (!controller.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (target.canChoose(source.getControllerId(), source, game)
                        && controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                    Card card = controller.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardsToExile(card, source, game, true, source.getSourceId(),
                                sourcePermanent.getIdName() + " (Imprint)");
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", CardUtil.addToolTipMarkTags(
                                    "[Imprinted card - " + card.getLogName() + ']'), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public IsochronScepterImprintEffect copy() {
        return new IsochronScepterImprintEffect(this);
    }

}

class IsochronScepterCopyEffect extends OneShotEffect {

    public IsochronScepterCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "You may copy the exiled card. If you do, "
                + "you may cast the copy without paying its mana cost";
    }

    public IsochronScepterCopyEffect(final IsochronScepterCopyEffect effect) {
        super(effect);
    }

    @Override
    public IsochronScepterCopyEffect copy() {
        return new IsochronScepterCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent scepter = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (scepter != null
                    && scepter.getImprinted() != null
                    && !scepter.getImprinted().isEmpty()) {
                Card imprintedInstant = game.getCard(scepter.getImprinted().get(0));
                if (imprintedInstant != null
                        && game.getState().getZone(imprintedInstant.getId()) == Zone.EXILED) {
                    if (controller.chooseUse(outcome, "Create a copy of " + imprintedInstant.getName() + '?', source, game)) {
                        Card copiedCard = game.copyCard(imprintedInstant, source, source.getControllerId());
                        if (copiedCard != null) {
                            game.getExile().add(source.getSourceId(), "", copiedCard);
                            game.getState().setZone(copiedCard.getId(), Zone.EXILED);
                            if (controller.chooseUse(outcome, "Cast the copied card without paying mana cost?", source, game)) {
                                if (copiedCard.getSpellAbility() != null) {
                                    game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), Boolean.TRUE);
                                    controller.cast(controller.chooseAbilityForCast(copiedCard, game, true),
                                            game, true, new ApprovingObject(source, game));
                                    game.getState().setValue("PlayFromNotOwnHandZone" + copiedCard.getId(), null);
                                } else {
                                    Logger.getLogger(IsochronScepterCopyEffect.class).error("Isochron Scepter: "
                                            + "spell ability == null " + copiedCard.getName());
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
