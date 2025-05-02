package mage.cards.p;

import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class PitOfOfferings extends CardImpl {

    public PitOfOfferings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // Pit of Offerings enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Pit of Offerings enters the battlefield, exile up to three target cards from graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PitOfOfferingsEffect(), false));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any of the exiled cards' colors.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new PitOfOfferingsManaEffect(), new TapSourceCost())
                .addHint(PitOfOfferingsHint.instance));
    }

    private PitOfOfferings(final PitOfOfferings card) {
        super(card);
    }

    @Override
    public PitOfOfferings copy() {
        return new PitOfOfferings(this);
    }
}

enum PitOfOfferingsHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        MageObject sourceObject = ability.getSourceObject(game);
        if (sourceObject == null) {
            return "";
        }

        Set<ObjectColor> exiledCardsColors = PitOfOfferingsManaEffect.getColorsExiled(sourceObject, game);

        List<String> manaText = new ArrayList<>();
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isWhite)) {
            manaText.add("{W}");
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlue)) {
            manaText.add("{U}");
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlack)) {
            manaText.add("{B}");
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isRed)) {
            manaText.add("{R}");
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isGreen)) {
            manaText.add("{G}");
        }

        if (manaText.isEmpty()) {
            return "";
        }

        return "Color of cards exiled: " + manaText.stream().collect(Collectors.joining(", "));
    }

    @Override
    public PitOfOfferingsHint copy() {
        return this;
    }
}

/**
 * Inspired by {@link mage.cards.c.ChromeMox}
 */
class PitOfOfferingsEffect extends OneShotEffect {

    static UUID getExileZoneId(MageObject sourceObject, Game game) {
        if (sourceObject == null) {
            return null;
        }
        return CardUtil.getExileZoneId(CardUtil.getObjectZoneString(
                "_pitOfOfferingExile", sourceObject.getId(), game,
                sourceObject.getZoneChangeCounter(game), false
        ), game);
    }

    private static final FilterCard filter = new FilterCard("cards from graveyards");

    public PitOfOfferingsEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to three target cards from graveyards";
    }

    private PitOfOfferingsEffect(final PitOfOfferingsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        TargetCard target = new TargetCard(0, 3, Zone.GRAVEYARD, filter);
        if (target.choose(outcome, source.getControllerId(), source.getSourceId(), source, game)) {
            Cards cardsExiled = new CardsImpl();
            cardsExiled.addAll(target.getTargets());
            controller.moveCardsToExile(
                    cardsExiled.getCards(game), source, game, true,
                    getExileZoneId(sourceObject, game), sourceObject.getIdName()
            );
        }
        return true;
    }

    @Override
    public PitOfOfferingsEffect copy() {
        return new PitOfOfferingsEffect(this);
    }

}

class PitOfOfferingsManaEffect extends ManaEffect {

    static Set<ObjectColor> getColorsExiled(MageObject sourceObject, Game game) {
        if (game == null) {
            return new HashSet<>();
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(PitOfOfferingsEffect.getExileZoneId(sourceObject, game));

        if (exileZone == null) {
            return new HashSet<>();
        }
        return exileZone
                .getCards(game)
                .stream()
                .map(c -> c.getColor(game))
                .collect(Collectors.toSet());
    }

    PitOfOfferingsManaEffect() {
        super();
        staticText = "Add one mana of any of the exiled cards' colors";
    }

    private PitOfOfferingsManaEffect(final PitOfOfferingsManaEffect effect) {
        super(effect);
    }

    @Override
    public PitOfOfferingsManaEffect copy() {
        return new PitOfOfferingsManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }

        MageObject sourceObject = source.getSourceObject(game);
        Set<ObjectColor> exiledCardsColors = getColorsExiled(sourceObject, game);
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlack)) {
            netMana.add(Mana.BlackMana(1));
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isRed)) {
            netMana.add(Mana.RedMana(1));
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlue)) {
            netMana.add(Mana.BlueMana(1));
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isGreen)) {
            netMana.add(Mana.GreenMana(1));
        }
        if (exiledCardsColors.stream().anyMatch(ObjectColor::isWhite)) {
            netMana.add(Mana.WhiteMana(1));
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || player == null || sourceObject == null) {
            return mana;
        }

        Set<ObjectColor> exiledCardsColors = getColorsExiled(sourceObject, game);

        if (!exiledCardsColors.isEmpty()) {
            Choice choice = new ChoiceColor(true);
            choice.getChoices().clear();
            choice.setMessage("Pick a mana color");
            if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlack)) {
                choice.getChoices().add("Black");
            }
            if (exiledCardsColors.stream().anyMatch(ObjectColor::isRed)) {
                choice.getChoices().add("Red");
            }
            if (exiledCardsColors.stream().anyMatch(ObjectColor::isBlue)) {
                choice.getChoices().add("Blue");
            }
            if (exiledCardsColors.stream().anyMatch(ObjectColor::isGreen)) {
                choice.getChoices().add("Green");
            }
            if (exiledCardsColors.stream().anyMatch(ObjectColor::isWhite)) {
                choice.getChoices().add("White");
            }
            if (!choice.getChoices().isEmpty()) {

                if (choice.getChoices().size() == 1) {
                    choice.setChoice(choice.getChoices().iterator().next());
                } else {
                    if (!player.choose(Outcome.PutManaInPool, choice, game)) {
                        return mana;
                    }
                }
                switch (choice.getChoice()) {
                    case "Black":
                        mana.add(Mana.BlackMana(1));
                        break;
                    case "Blue":
                        mana.add(Mana.BlueMana(1));
                        break;
                    case "Red":
                        mana.add(Mana.RedMana(1));
                        break;
                    case "Green":
                        mana.add(Mana.GreenMana(1));
                        break;
                    case "White":
                        mana.add(Mana.WhiteMana(1));
                        break;
                    default:
                        break;
                }
            }
        }
        return mana;
    }

}
