package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxgardArmory extends CardImpl {

    public AxgardArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Axgard Armory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{R}{R}{W}, {T}: Sacrifice Axgard Armory: Search your library for an Aura card and/or Equipment card, reveal them, put them into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new AxgardArmoryTarget(), true)
                        .setText("search your library for an Aura card and/or an Equipment card, reveal them, " +
                                "put them into your hand, then shuffle"),
                new ManaCostsImpl<>("{1}{R}{R}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private AxgardArmory(final AxgardArmory card) {
        super(card);
    }

    @Override
    public AxgardArmory copy() {
        return new AxgardArmory(this);
    }
}

class AxgardArmoryTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("an Aura card and/or an Equipment card");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner
            = new SubTypeAssignment(SubType.AURA, SubType.EQUIPMENT);

    AxgardArmoryTarget() {
        super(0, 2, filter);
    }

    private AxgardArmoryTarget(final AxgardArmoryTarget target) {
        super(target);
    }

    @Override
    public AxgardArmoryTarget copy() {
        return new AxgardArmoryTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
