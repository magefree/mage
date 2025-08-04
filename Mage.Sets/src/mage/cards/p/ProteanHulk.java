package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class ProteanHulk extends CardImpl {

    public ProteanHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Protean Hulk dies, search your library for any number of creature cards with total converted mana cost 6 or less and put them onto the battlefield. Then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(new ProteanHulkTarget())));
    }

    private ProteanHulk(final ProteanHulk card) {
        super(card);
    }

    @Override
    public ProteanHulk copy() {
        return new ProteanHulk(this);
    }
}

class ProteanHulkTarget extends TargetCardInLibrary {

    private static final FilterCard filterStatic
            = new FilterCreatureCard("creature cards with total mana value 6 or less");

    ProteanHulkTarget() {
        super(0, Integer.MAX_VALUE, filterStatic);
    }

    private ProteanHulkTarget(final ProteanHulkTarget target) {
        super(target);
    }

    @Override
    public ProteanHulkTarget copy() {
        return new ProteanHulkTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 6, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 6, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
