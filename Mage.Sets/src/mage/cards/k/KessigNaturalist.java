package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KessigNaturalist extends CardImpl {

    public KessigNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.l.LordOfTheUlvenwald.class;

        // Whenever Kessig Naturalist attacks, add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new KessigNaturalistEffect()));

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
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
