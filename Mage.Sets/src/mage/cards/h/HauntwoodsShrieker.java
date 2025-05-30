package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author Grath
 */
public final class HauntwoodsShrieker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("face down creature");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public HauntwoodsShrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Hauntwoods Shrieker attacks, manifest dread.
        this.addAbility(new AttacksTriggeredAbility(new ManifestDreadEffect()));

        // {1}{G}: Reveal target face-down permanent. If it's a creature card, you may turn it face up.
        Ability ability = new SimpleActivatedAbility(new HauntwoodsShriekerEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HauntwoodsShrieker(final HauntwoodsShrieker card) {
        super(card);
    }

    @Override
    public HauntwoodsShrieker copy() {
        return new HauntwoodsShrieker(this);
    }
}

class HauntwoodsShriekerEffect extends OneShotEffect {

    HauntwoodsShriekerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal target face-down permanent. If it's a creature card, you may turn it face up";
    }

    private HauntwoodsShriekerEffect(final HauntwoodsShriekerEffect effect) {
        super(effect);
    }

    @Override
    public HauntwoodsShriekerEffect copy() {
        return new HauntwoodsShriekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (player == null || mageObject == null) {
            return false;
        }
        Permanent faceDownPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (faceDownPermanent != null) {
            Card trueCard = (Card)faceDownPermanent.getBasicMageObject();
            player.revealCards(source, new CardsImpl(faceDownPermanent), game);
            if (trueCard.isCreature() && player.chooseUse(
                    faceDownPermanent.getControllerId() == source.getControllerId() ? Outcome.Benefit : Outcome.Detriment,
                    "Turn " + trueCard.getName() + " face up?", source, game)) {
                return faceDownPermanent.turnFaceUp(source, game, source.getControllerId());
            }
        } else {
            return false;
        }
        return true;
    }
}
