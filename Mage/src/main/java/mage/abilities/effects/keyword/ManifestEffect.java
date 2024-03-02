package mage.abilities.effects.keyword;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;

/**
 * Manifest
 * <p>
 * 701.34a
 * To manifest a card, turn it face down. It becomes a 2/2 face-down creature card with no text, no name, no subtypes,
 * and no mana cost. Put that card onto the battlefield face down. That permanent is a manifested permanent for as
 * long as it remains face down. The effect defining its characteristics works while the card is face down and ends
 * when it’s turned face up.
 * <p>
 * 701.34b
 * Any time you have priority, you may turn a manifested permanent you control face up. This is a special action
 * that doesn’t use the stack (see rule 116.2b). To do this, show all players that the card representing that
 * permanent is a creature card and what that card’s mana cost is, pay that cost, then turn the permanent face up.
 * The effect defining its characteristics while it was face down ends, and it regains its normal characteristics.
 * (If the card representing that permanent isn’t a creature card or it doesn’t have a mana cost, it can’t be turned
 * face up this way.)
 * <p>
 * 701.34c TODO: need support it
 * If a card with morph is manifested, its controller may turn that card face up using either the procedure
 * described in rule 702.37e to turn a face-down permanent with morph face up or the procedure described above
 * to turn a manifested permanent face up.
 * <p>
 * 701.34d TODO: need support it
 * If a card with disguise is manifested, its controller may turn that card face up using either the procedure
 * described in rule 702.168d to turn a face-down permanent with disguise face up or the procedure described
 * above to turn a manifested permanent face up.
 * <p>
 * 701.34e TODO: need support it
 * If an effect instructs a player to manifest multiple cards from their library, those cards are manifested one at a time.
 * <p>
 * 701.34f TODO: need research, possible buggy (see face down effect order doManifestCards)
 * If an effect instructs a player to manifest a card and a rule or effect prohibits the face-down object from
 * entering the battlefield, that card isn’t manifested. Its characteristics remain unmodified and it remains in
 * its previous zone. If it was face up, it remains face up.
 * <p>
 * 701.34g TODO: need support it
 * If a manifested permanent that’s represented by an instant or sorcery card would turn face up, its controller
 * reveals it and leaves it face down. Abilities that trigger whenever a permanent is turned face up won’t trigger.
 *
 * @author LevelX2, JayDi85
 */
public class ManifestEffect extends OneShotEffect {

    private final DynamicValue amount;
    private final boolean isPlural;

    public ManifestEffect(int amount) {
        this(StaticValue.get(amount), amount > 1);
    }

    public ManifestEffect(DynamicValue amount) {
        this(amount, true);
    }

    private ManifestEffect(DynamicValue amount, boolean isPlural) {
        super(Outcome.PutCreatureInPlay);
        this.amount = amount;
        this.isPlural = isPlural;
        this.staticText = setText();
    }

    private ManifestEffect(final ManifestEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.isPlural = effect.isPlural;
    }

    @Override
    public ManifestEffect copy() {
        return new ManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int manifestAmount = amount.calculate(game, source, this);
        return doManifestCards(game, source, controller, controller.getLibrary().getTopCards(game, manifestAmount));
    }

    public static boolean doManifestCards(Game game, Ability source, Player manifestPlayer, Set<Card> cardsToManifest) {
        if (cardsToManifest.isEmpty()) {
            return false;
        }

        // prepare source ability
        // TODO: looks buggy, must not change source ability!
        // TODO: looks buggy, if target player manifested then source's controllerId will be wrong (not who manifested)
        //  so BecomesFaceDownCreatureEffect will see wrong source.controllerId
        //  (possible bugs: keep manifested after player leave/lose?)
        Ability newSource = source.copy();
        newSource.setWorksFaceDown(true);

        // prepare face down effect for battlefield permanents
        // TODO: need research - why it add effect before move?!
        for (Card card : cardsToManifest) {
            Card battlefieldCard = BecomesFaceDownCreatureEffect.findDefaultCardSideForFaceDown(game, card);

            // search mana cost for a face up ability (look at face side of the double side card)
            ManaCosts manaCosts = null;
            if (battlefieldCard.isCreature(game)) {
                manaCosts = battlefieldCard.getSpellAbility() != null ? battlefieldCard.getSpellAbility().getManaCosts() : null;
                if (manaCosts == null) {
                    manaCosts = new ManaCostsImpl<>("{0}");
                }
            }

            // zcc + 1 for use case with Rally the Ancestors (see related test)
            MageObjectReference objectReference = new MageObjectReference(battlefieldCard.getId(), battlefieldCard.getZoneChangeCounter(game) + 1, game);
            game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, FaceDownType.MANIFESTED), newSource);
        }

        // move cards to battlefield as face down
        // TODO: possible buggy for multiple cards, see rule 701.34e - it require manifest one by one (card to check: Omarthis, Ghostfire Initiate)
        manifestPlayer.moveCards(cardsToManifest, Zone.BATTLEFIELD, source, game, false, true, false, null);
        for (Card card : cardsToManifest) {
            Card battlefieldCard = BecomesFaceDownCreatureEffect.findDefaultCardSideForFaceDown(game, card);

            Permanent permanent = game.getPermanent(battlefieldCard.getId());
            if (permanent != null) {
                // TODO: permanent already has manifested status, so code can be deleted later
                // TODO: add test with battlefield trigger/watcher (must not see normal card, must not see face down status without manifest)
                permanent.setManifested(true);
            } else {
                // TODO: looks buggy, card can't be moved to battlefield, but face down effect already active
                //  or it can be face down on another move to battalefield
            }
        }

        return true;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("manifest the top ");
        if (isPlural) {
            sb.append(CardUtil.numberToText(amount.toString())).append(" cards ");
        } else {
            sb.append("card ");
        }
        sb.append("of your library. ");
        if (isPlural) {
            sb.append("<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. You may turn it face up at any time for its mana cost if it's a creature card.)</i>");
        } else {
            sb.append("<i>(Put it onto the battlefield face down as a 2/2 creature. Turn it face up at any time for its mana cost if it's a creature card.)</i>");
        }
        return sb.toString();
    }
}
