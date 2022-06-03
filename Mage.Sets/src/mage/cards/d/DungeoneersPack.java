package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DungeoneersPack extends CardImpl {

    public DungeoneersPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Dungeoneer's Pack enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {2}, {T}, Sacrifice Dungeoneer's Pack: You take the initiative, gain 3 life, draw a card, and create a Treasure token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new TakeTheInitiativeEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new GainLifeEffect(3).concatBy(", you"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(","));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy(", and"));
        this.addAbility(ability);
    }

    private DungeoneersPack(final DungeoneersPack card) {
        super(card);
    }

    @Override
    public DungeoneersPack copy() {
        return new DungeoneersPack(this);
    }
}
