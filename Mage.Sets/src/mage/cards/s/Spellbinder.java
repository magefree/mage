package mage.cards.s;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Spellbinder extends CardImpl {

    public Spellbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Imprint - When Spellbinder enters the battlefield, you may exile 
        // an instant card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SpellbinderImprintEffect(), true)
                .setAbilityWord(AbilityWord.IMPRINT)
        );

        // Whenever equipped creature deals combat damage to a player, you may 
        // copy the exiled card. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new SpellbinderTriggeredAbility());

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));
    }

    private Spellbinder(final Spellbinder card) {
        super(card);
    }

    @Override
    public Spellbinder copy() {
        return new Spellbinder(this);
    }
}

class SpellbinderTriggeredAbility extends TriggeredAbilityImpl {

    SpellbinderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpellbinderCopyEffect(), true);
    }

    SpellbinderTriggeredAbility(final SpellbinderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellbinderTriggeredAbility copy() {
        return new SpellbinderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent p = game.getPermanent(event.getSourceId());
        return damageEvent.isCombatDamage()
                && p != null
                && p.getAttachments().contains(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, "
                + "you may copy the exiled card. If you do, you may cast "
                + "the copy without paying its mana cost.";
    }
}

class SpellbinderImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("instant card");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public SpellbinderImprintEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile an instant card from your hand";
    }

    public SpellbinderImprintEffect(SpellbinderImprintEffect effect) {
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
                        controller.moveCardToExileWithInfo(card, source.getSourceId(),
                                sourcePermanent.getIdName() + " (Imprint)", source, game, Zone.HAND, true);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - "
                                    + card.getLogName() + ']'), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public SpellbinderImprintEffect copy() {
        return new SpellbinderImprintEffect(this);
    }

}

class SpellbinderCopyEffect extends OneShotEffect {

    public SpellbinderCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "You may copy the exiled card. If you do, "
                + "you may cast the copy without paying its mana cost";
    }

    public SpellbinderCopyEffect(final SpellbinderCopyEffect effect) {
        super(effect);
    }

    @Override
    public SpellbinderCopyEffect copy() {
        return new SpellbinderCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent spellbinder = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (spellbinder != null && spellbinder.getImprinted() != null && !spellbinder.getImprinted().isEmpty()) {
                Card imprintedInstant = game.getCard(spellbinder.getImprinted().get(0));
                if (imprintedInstant != null && game.getState().getZone(imprintedInstant.getId()) == Zone.EXILED) {
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
                                    Logger.getLogger(SpellbinderCopyEffect.class).error(
                                            "Spellbinder: spell ability == null " + copiedCard.getName());
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
