package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.GameLog;

/**
 *
 * @author Plopman
 */
public final class ChromeMox extends CardImpl {

    public ChromeMox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // Imprint - When Chrome Mox enters the battlefield, you may exile a nonartifact, nonland card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ChromeMoxEffect(), true));
        // {T}: Add one mana of any of the exiled card's colors.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new ChromeMoxManaEffect(), new TapSourceCost()));
    }

    public ChromeMox(final ChromeMox card) {
        super(card);
    }

    @Override
    public ChromeMox copy() {
        return new ChromeMox(this);
    }
}

class ChromeMoxEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonartifact, nonland card");

    static {
        filter.add(Predicates.not(Predicates.or(new CardTypePredicate(CardType.LAND), new CardTypePredicate(CardType.ARTIFACT))));
    }

    public ChromeMoxEffect() {
        super(Outcome.Benefit);
        staticText = "exile a nonartifact, nonland card from your hand";
    }

    public ChromeMoxEffect(ChromeMoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setNotTarget(true);
            Card cardToImprint = null;
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (!controller.getHand().isEmpty() && controller.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                cardToImprint = controller.getHand().get(target.getFirstTarget(), game);
            }
            if (sourcePermanent != null) {
                if (cardToImprint != null) {
                    controller.moveCardsToExile(cardToImprint, source, game, true, source.getSourceId(), sourceObject.getIdName() + " (Imprint)");
                    sourcePermanent.imprint(cardToImprint.getId(), game);
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + GameLog.getColoredObjectIdNameForTooltip(cardToImprint) + ']'), game);
                } else {
                    sourcePermanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - None]"), game);
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public ChromeMoxEffect copy() {
        return new ChromeMoxEffect(this);
    }

}

class ChromeMoxManaEffect extends ManaEffect {

    ChromeMoxManaEffect() {
        super();
        staticText = "Add one mana of any of the exiled card's colors";
    }

    ChromeMoxManaEffect(ChromeMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public ChromeMoxManaEffect copy() {
        return new ChromeMoxManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = getMana(game, source);
            checkToFirePossibleEvents(mana, game, source);
            controller.getManaPool().addMana(mana, game, source);
            return true;

        }
        return false;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            List<UUID> imprinted = permanent.getImprinted();
            if (!imprinted.isEmpty()) {
                Card imprintedCard = game.getCard(imprinted.get(0));
                if (imprintedCard != null) {
                    ObjectColor color = imprintedCard.getColor(game);
                    if (color.isBlack()) {
                        netMana.add(Mana.BlackMana(1));
                    }
                    if (color.isRed()) {
                        netMana.add(Mana.RedMana(1));
                    }
                    if (color.isBlue()) {
                        netMana.add(Mana.BlueMana(1));
                    }
                    if (color.isGreen()) {
                        netMana.add(Mana.GreenMana(1));
                    }
                    if (color.isWhite()) {
                        netMana.add(Mana.WhiteMana(1));
                    }
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            List<UUID> imprinted = permanent.getImprinted();
            if (!imprinted.isEmpty()) {
                Card imprintedCard = game.getCard(imprinted.get(0));
                if (imprintedCard != null) {
                    Choice choice = new ChoiceColor(true);
                    choice.getChoices().clear();
                    choice.setMessage("Pick a mana color");
                    ObjectColor color = imprintedCard.getColor(game);
                    if (color.isBlack()) {
                        choice.getChoices().add("Black");
                    }
                    if (color.isRed()) {
                        choice.getChoices().add("Red");
                    }
                    if (color.isBlue()) {
                        choice.getChoices().add("Blue");
                    }
                    if (color.isGreen()) {
                        choice.getChoices().add("Green");
                    }
                    if (color.isWhite()) {
                        choice.getChoices().add("White");
                    }
                    Mana mana = new Mana();
                    if (!choice.getChoices().isEmpty()) {

                        if (choice.getChoices().size() == 1) {
                            choice.setChoice(choice.getChoices().iterator().next());
                        } else {
                            if (!player.choose(outcome, choice, game)) {
                                return null;
                            }
                        }
                        switch (choice.getChoice()) {
                            case "Black":
                                player.getManaPool().addMana(Mana.BlackMana(1), game, source);
                                break;
                            case "Blue":
                                player.getManaPool().addMana(Mana.BlueMana(1), game, source);
                                break;
                            case "Red":
                                player.getManaPool().addMana(Mana.RedMana(1), game, source);
                                break;
                            case "Green":
                                player.getManaPool().addMana(Mana.GreenMana(1), game, source);
                                break;
                            case "White":
                                player.getManaPool().addMana(Mana.WhiteMana(1), game, source);
                                break;
                            default:
                                break;
                        }

                    }
                    return mana;
                }
            }
        }
        return null;
    }

}
