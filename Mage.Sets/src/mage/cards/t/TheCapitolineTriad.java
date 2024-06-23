package mage.cards.t;

import java.awt.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.ExileManaValueFromGraveyardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.ManaValueInGraveyard;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.command.emblems.TheCapitolineTriadEmblem;
import mage.game.permanent.token.CitizenToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 * @author grimreap124
 */
public final class TheCapitolineTriad extends CardImpl {

    private static final FilterCard filter = new FilterCard("historic card");
    private static final DynamicValue costXValue = new CardsInControllerGraveyardCount(filter);
    private static final DynamicValue manaValueGraveyard = new ManaValueInGraveyard(filter);

    static {
        filter.add(HistoricPredicate.instance);
    }

    public TheCapitolineTriad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Those Who Came Before - This spell costs 1 less to cast for each historic card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Historic cards in your graveyard", costXValue));
        ability.withFlavorWord("Those Who Came Before");
        this.addAbility(ability);


        // Exile any number of historic cards from your graveyard with total mana value 30 or greater: You get an emblem with "Creatures you control have base power and toughness 9/9."
        ability = new SimpleActivatedAbility(new GetEmblemEffect(new TheCapitolineTriadEmblem()), new ExileManaValueFromGraveyardCost(new TheCapitolineTriadTarget(), 30));
        ability.addHint(new ValueHint("Mana value of selected cards", manaValueGraveyard));
        this.addAbility(ability);
    }

    private TheCapitolineTriad(final TheCapitolineTriad card) {
        super(card);
    }

    @Override
    public TheCapitolineTriad copy() {
        return new TheCapitolineTriad(this);
    }
}

class TheCapitolineTriadTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter = new FilterCard("historic cards");

    static {
        filter.add(HistoricPredicate.instance);
    }

    TheCapitolineTriadTarget() {
        super(1, Integer.MAX_VALUE, filter, true);
    }

    private TheCapitolineTriadTarget(final TheCapitolineTriadTarget target) {
        super(target);
    }

    @Override
    public TheCapitolineTriadTarget copy() {
        return new TheCapitolineTriadTarget(this);
    }

    @Override
    public boolean isChosen(Game game) {
        return super.isChosen(game) && metCondition(this.getTargets(), game);
    }

    @Override
    public String getMessage(Game game) {
        String text = "Select " + CardUtil.addArticle(targetName);
        int manaValueOfSelection = manaValueOfSelection(this.getTargets(), game);
        text += " (selected " + this.getTargets().size() + " cards; card types: ";
        text += HintUtils.prepareText(
                manaValueOfSelection + " of 30",
                manaValueOfSelection >= 30 ? Color.GREEN : Color.RED
        );
        text +=")";
        return text;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (!super.canChoose(sourceControllerId, source, game)) {
            return false;
        }
        // Check that exiling all the possible cards would have >= 4 different card types
        return metCondition(this.possibleTargets(sourceControllerId, source, game), game);
    }

    private static int manaValueOfSelection(Collection<UUID> cardsIds, Game game) {
        return cardsIds
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull).mapToInt(MageObject::getManaValue).sum();
    }

    private static boolean metCondition(Collection<UUID> cardsIds, Game game) {
        return manaValueOfSelection(cardsIds, game) >= 30;
    }
}