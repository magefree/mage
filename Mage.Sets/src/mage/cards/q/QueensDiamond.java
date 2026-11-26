package mage.cards.q;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class QueensDiamond extends CardImpl {

    public QueensDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // {T}: Choose a color. Add one mana of that color for each charge counter on Queen's Diamond. Spend this mana only to cast your commander.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new QueensDiamondManaEffect(), new TapSourceCost()));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Vigilance
        // Haste
        // 5/5
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(VigilanceAbility.getInstance())
                .withLevelAbility(HasteAbility.getInstance())
                .withPT(5, 5));
    }

    private QueensDiamond(final QueensDiamond card) {
        super(card);
    }

    @Override
    public QueensDiamond copy() {
        return new QueensDiamond(this);
    }
}

class QueensDiamondManaEffect extends ManaEffect {

    private static final FilterSpell filter = new FilterSpell("your commander");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    private final ConditionalManaBuilder manaBuilder = new ConditionalSpellManaBuilder(filter);

    QueensDiamondManaEffect() {
        super();
        this.staticText = "Choose a color. Add one mana of that color for each charge counter on {this}. " +
                "Spend this mana only to cast your commander";
    }

    private QueensDiamondManaEffect(final QueensDiamondManaEffect effect) {
        super(effect);
    }

    @Override
    public QueensDiamondManaEffect copy() {
        return new QueensDiamondManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return netMana;
        }
        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
        if (counters < 1) {
            return netMana;
        }
        // one option only, as several would be merged into unconditional mana and lose the restriction
        netMana.add(manaBuilder.setMana(Mana.AnyMana(counters), source, game).build());
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return null;
        }
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent == null || controller == null) {
            return null;
        }
        int counters = sourcePermanent.getCounters(game).getCount(CounterType.CHARGE);
        if (counters < 1) {
            return null;
        }
        ChoiceColor choice = new ChoiceColor();
        choice.setMessage("Choose a color to add mana of that color");
        if (!controller.choose(outcome, choice, game)) {
            return null;
        }
        ManaType chosenType = ManaType.findByName(choice.getChoice());
        if (chosenType == null) {
            return null;
        }
        return manaBuilder.setMana(new Mana(chosenType, counters), source, game).build();
    }
}
