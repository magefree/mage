package mage.cards.k;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KessigNaturalist extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wolves and Werewolves");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public KessigNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{R}{G}",
                "Lord of the Ulvenwald",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "RG");

        // Kessig Naturalist
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever Kessig Naturalist attacks, add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new KessigNaturalistEffect()));

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Lord of the Ulvenwald
        this.getRightHalfCard().setPT(3, 3);

        // Other Wolves and Werewolves you control get +1/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Lord of the Ulvenwald attacks, add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new KessigNaturalistEffect()));

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private KessigNaturalist(final KessigNaturalist card) {
        super(card);
    }

    @Override
    public KessigNaturalist copy() {
        return new KessigNaturalist(this);
    }
}

class KessigNaturalistEffect extends OneShotEffect {

    KessigNaturalistEffect() {
        super(Outcome.Benefit);
        staticText = "add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private KessigNaturalistEffect(final KessigNaturalistEffect effect) {
        super(effect);
    }

    @Override
    public KessigNaturalistEffect copy() {
        return new KessigNaturalistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Mana mana = player.chooseUse(
                Outcome.Neutral, "Choose red or green", null,
                "Red", "Green", source, game
        ) ? Mana.RedMana(1) : Mana.GreenMana(1);
        player.getManaPool().addMana(mana, game, source, true);
        return true;
    }
}
