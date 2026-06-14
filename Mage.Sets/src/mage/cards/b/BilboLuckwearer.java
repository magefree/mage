package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class BilboLuckwearer extends AdventureCard {

    public BilboLuckwearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{U}", "Burglar's Plot", "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bilbo can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Bilbo deals combat damage to a player, draw a card, then discard a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DrawDiscardControllerEffect(1, 1), false
        ));

        // Burglar's Plot
        // Exchange control of two target nonland permanents that share a card type.
        this.getSpellCard().getSpellAbility().addEffect(new ExchangeControlTargetEffect(
                Duration.EndOfGame, "Exchange control of two target permanents that share a card type."
        ));
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanentsThatShareCardType());

        this.finalizeAdventure();
    }

    private BilboLuckwearer(final BilboLuckwearer card) {
        super(card);
    }

    @Override
    public BilboLuckwearer copy() {
        return new BilboLuckwearer(this);
    }
}

class TargetPermanentsThatShareCardType extends TargetPermanent {

    TargetPermanentsThatShareCardType() {
        super(2, 2, StaticFilters.FILTER_PERMANENT, false);
        targetName = "permanents that share a card type";
    }

    private TargetPermanentsThatShareCardType(final TargetPermanentsThatShareCardType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (super.canTarget(playerId, id, source, game)) {
            if (!getTargets().isEmpty()) {
                Permanent targetOne = game.getPermanent(getTargets().get(0));
                Permanent targetTwo = game.getPermanent(id);
                if (targetOne == null || targetTwo == null) {
                    return false;
                }
                return targetOne.shareTypes(targetTwo, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        Set<CardType> cardTypes = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        if (targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, source, game)) {
                    for (CardType cardType : permanent.getCardType(game)) {
                        if (cardTypes.contains(cardType)) {
                            return true;
                        }
                    }
                    cardTypes.addAll(permanent.getCardType(game));
                }
            }
        }
        return false;
    }

    @Override
    public TargetPermanentsThatShareCardType copy() {
        return new TargetPermanentsThatShareCardType(this);
    }
}
