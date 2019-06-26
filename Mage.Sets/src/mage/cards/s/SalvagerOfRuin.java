package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SalvagerOfRuin extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent card in your graveyard that were put there from the battlefield this turn"
    );

    static {
        filter.add(SalvagerOfRuinPredicate.instance);
    }

    public SalvagerOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice Salvager of Ruin: Choose target permanent card in your graveyard that was put there from the battlefield this turn. Return it to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect().setText(
                "Choose target permanent card in your graveyard " +
                        "that was put there from the battlefield this turn. " +
                        "Return it to your hand."
        ), new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(1, filter));
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private SalvagerOfRuin(final SalvagerOfRuin card) {
        super(card);
    }

    @Override
    public SalvagerOfRuin copy() {
        return new SalvagerOfRuin(this);
    }
}

enum SalvagerOfRuinPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        return watcher != null
                && watcher.getCardsPutToGraveyardFromBattlefield().contains(new MageObjectReference(input, game));
    }
}
