package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */

public final class CassHandOfVengeance extends CardImpl {

    public CassHandOfVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Cass or another creature you control dies, if it was enchanted or equipped, return any number of Aura cards that were attached to it from your graveyard to the battlefield attached to target creature, then attach any number of Equipment that were attached to it to that creature.
        TriggeredAbility ability = new DiesThisOrAnotherTriggeredAbility(
            new CassHandOfVengeanceEffect(),
            false,
            StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ).withInterveningIf(CassHandOfVengeanceCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CassHandOfVengeance(final CassHandOfVengeance card) {
        super(card);
    }

    @Override
    public CassHandOfVengeance copy() {
        return new CassHandOfVengeance(this);
    }
}

enum CassHandOfVengeanceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) source.getEffects().get(0).getValue("creatureDied");
        if (creature == null) {
            return false;
        }
        return creature.getAttachments()
            .stream()
            .map(game::getPermanentOrLKIBattlefield)
            .filter(Objects::nonNull)
            .anyMatch(attachment -> attachment.isEnchantment(game) || attachment.hasSubtype(SubType.EQUIPMENT, game));
    }

    @Override
    public String toString() {
        return "it was enchanted or equipped";
    }
}

class CassHandOfVengeanceEffect extends OneShotEffect {

    CassHandOfVengeanceEffect() {
        super(Outcome.Benefit);
        staticText = "return any number of Aura cards that were attached to it from your graveyard to the battlefield " +
            "attached to target creature, then attach any number of Equipment that were attached to it to that creature";
    }

    private CassHandOfVengeanceEffect(final CassHandOfVengeanceEffect effect) {
        super(effect);
    }

    @Override
    public CassHandOfVengeanceEffect copy() {
        return new CassHandOfVengeanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent dyingCreature = (Permanent) getValue("creatureDied");
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (dyingCreature == null || targetCreature == null || controller == null) {
            return false;
        }

        Cards validAuras = new CardsImpl();
        List<Permanent> validEquipment = new ArrayList<>();
        collectAttachments(dyingCreature, controller, game, targetCreature.getId(), validAuras, validEquipment);

        returnAuras(validAuras, targetCreature, controller, source, game);
        reattachEquipment(validEquipment, targetCreature, controller, source, game);
        return true;
    }

    private void collectAttachments(Permanent dyingCreature, Player controller, Game game, UUID targetCreatureId, Cards validAuras, List<Permanent> validEquipment) {
        FilterCard auraFilter = new FilterCard("Aura cards from your graveyard that were attached to the dying creature");
        auraFilter.add(SubType.AURA.getPredicate());
        auraFilter.add(new AuraCardCanAttachToPermanentId(targetCreatureId));
        for (UUID attachmentId : dyingCreature.getAttachments()) {
            Permanent lki = game.getPermanentOrLKIBattlefield(attachmentId);
            if (lki == null) {
                continue;
            }
            if (lki.isEnchantment(game)) {
                Card card = game.getCard(attachmentId);
                if (card != null && controller.getGraveyard().contains(attachmentId)
                        && auraFilter.match(card, game)) {
                    validAuras.add(card);
                }
            } else if (lki.hasSubtype(SubType.EQUIPMENT, game)) {
                Permanent eq = game.getPermanent(attachmentId);
                if (eq != null) {
                    validEquipment.add(eq);
                }
            }
        }
    }

    private void returnAuras(Cards validAuras, Permanent targetCreature, Player controller, Ability source, Game game) {
        if (validAuras.isEmpty()) {
            return;
        }
        TargetCard auraTarget = new TargetCard(0, validAuras.size(), Zone.GRAVEYARD,
                new FilterCard("Aura cards from your graveyard that were attached to the dying creature"));
        controller.chooseTarget(Outcome.Benefit, validAuras, auraTarget, source, game);
        for (UUID id : auraTarget.getTargets()) {
            Card card = game.getCard(id);
            if (card == null) {
                continue;
            }
            game.getState().setValue("attachTo:" + card.getId(), targetCreature);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent aura = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (aura != null) {
                targetCreature.addAttachment(aura.getId(), source, game);
            }
        }
    }

    private void reattachEquipment(List<Permanent> validEquipment, Permanent targetCreature, Player controller, Ability source, Game game) {
        if (validEquipment.isEmpty()) {
            return;
        }
        List<Predicate<MageObject>> idPredicates = new ArrayList<>();
        for (Permanent p : validEquipment) {
            idPredicates.add(new CardIdPredicate(p.getId()));
        }
        FilterPermanent equipFilter = new FilterPermanent("Equipment that were attached to the dying creature");
        equipFilter.add(Predicates.or(idPredicates));
        TargetPermanent equipTarget = new TargetPermanent(0, validEquipment.size(), equipFilter, true);
        controller.choose(Outcome.Benefit, equipTarget, source, game);
        for (UUID id : equipTarget.getTargets()) {
            targetCreature.addAttachment(id, source, game);
        }
    }
}
