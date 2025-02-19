package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EnchantedAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MantleOfTheAncients extends CardImpl {

    public MantleOfTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Mantle of the Ancients enters the battlefield, return any number of target Aura and/or Equipment cards from your graveyard to the battlefield attached to enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MantleOfTheAncientsEffect()));

        // Enchanted creature gets +1/+1 for each Aura and Equipment attached to it.
        DynamicValue auraAndEquipmentCount = new EnchantedAttachedCount(SubType.AURA, SubType.EQUIPMENT);
        this.addAbility(new SimpleStaticAbility(
                new BoostEnchantedEffect(auraAndEquipmentCount, auraAndEquipmentCount)
                        .setText("enchanted creature gets +1/+1 for each Aura and Equipment attached to it")));
    }

    private MantleOfTheAncients(final MantleOfTheAncients card) {
        super(card);
    }

    @Override
    public MantleOfTheAncients copy() {
        return new MantleOfTheAncients(this);
    }
}

class MantleOfTheAncientsEffect extends OneShotEffect {

    MantleOfTheAncientsEffect() {
        super(Outcome.Benefit);
        staticText = "return any number of target Aura and/or Equipment cards " +
                "that could be attached to enchanted creature from your graveyard " +
                "to the battlefield attached to enchanted creature";
    }

    private MantleOfTheAncientsEffect(final MantleOfTheAncientsEffect effect) {
        super(effect);
    }

    @Override
    public MantleOfTheAncientsEffect copy() {
        return new MantleOfTheAncientsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (player == null || sourcePermanent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        FilterCard filter = new FilterCard("Aura or Equipment card that can be attached to " + permanent.getName());
        filter.add(new MantleOfTheAncientsPredicate(permanent));
        TargetCard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter, true);
        player.choose(outcome, target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        if (cards.isEmpty()) {
            return false;
        }
        cards.getCards(game)
                .stream()
                .forEach(card -> game.getState().setValue("attachTo:" + card.getId(), permanent));
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        for (UUID cardId : cards) {
            permanent.addAttachment(cardId, source, game);
        }
        return true;
    }
}

class MantleOfTheAncientsPredicate implements Predicate<Card> {

    private final Permanent permanent;

    MantleOfTheAncientsPredicate(Permanent permanent) {
        this.permanent = permanent;
    }

    @Override
    public boolean apply(Card input, Game game) {
        if (input.hasSubtype(SubType.AURA, game)) {
            return input
                    .getSpellAbility()
                    .getTargets()
                    .stream()
                    .anyMatch(target -> target.getFilter().match(permanent, game));
        }
        return input.hasSubtype(SubType.EQUIPMENT, game);
    }
}
