package mage.cards.o;

import java.util.UUID;
import java.util.stream.Collectors;

import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class OrcristGoblinCleaver extends CardImpl {

    public OrcristGoblinCleaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has trample"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, choose a creature type. Create a Treasure token for each creature you control of that type.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
            new OrcristGoblinCleaverEffect(), "equipped creature", false
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));

    }

    private OrcristGoblinCleaver(final OrcristGoblinCleaver card) {
        super(card);
    }

    @Override
    public OrcristGoblinCleaver copy() {
        return new OrcristGoblinCleaver(this);
    }
}

class OrcristGoblinCleaverEffect extends OneShotEffect {

    public OrcristGoblinCleaverEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature type. Create a Treasure token for each creature you control of that type";
    }

    private OrcristGoblinCleaverEffect(final OrcristGoblinCleaverEffect effect) {
        super(effect);
    }

    @Override
    public OrcristGoblinCleaverEffect copy() {
        return new OrcristGoblinCleaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Choice choice = new ChoiceCreatureType(game, source);
        controller.choose(outcome, choice, game);
        SubType subType = SubType.byDescription(choice.getChoiceKey());
        if (subType == null) {
            return false;
        }
        game.informPlayers(controller.getLogName() + " chooses " + subType);

        int count = game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_CONTROLLED_CREATURE,
            source.getControllerId(), source, game
        ).stream()
            .filter(permanent -> permanent.hasSubtype(subType, game))
            .collect(Collectors.toList())
            .size();

        if (count > 0) {
            new CreateTokenEffect(new TreasureToken(), count).apply(game, source);
        }

        return true;
    }
}
