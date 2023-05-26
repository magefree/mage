package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoloGuideToMonsters extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell(
            "a creature spell that doesn't share a creature type " +
                    "with a creature you control or a creature card in your graveyard"
    );

    static {
        filter.add(VoloGuideToMonstersPredicate.instance);
    }

    public VoloGuideToMonsters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a creature spell that doesn't share a creature type with a creature you control or a creature card in your graveyard, copy that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(true)
                        .setText("copy that spell"),
                filter, false, true
        ));
    }

    private VoloGuideToMonsters(final VoloGuideToMonsters card) {
        super(card);
    }

    @Override
    public VoloGuideToMonsters copy() {
        return new VoloGuideToMonsters(this);
    }
}

enum VoloGuideToMonstersPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        Player player = game.getPlayer(input.getControllerId());
        if (player != null
                && player
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .stream()
                .anyMatch(card -> input.shareCreatureTypes(game, card))) {
            return false;
        }
        return game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, input.getControllerId(), game)
                .stream()
                .noneMatch(permanent -> input.shareCreatureTypes(game, permanent));
    }
}
