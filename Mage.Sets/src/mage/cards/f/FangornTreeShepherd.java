package mage.cards.f;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.YouDontLoseManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangornTreeShepherd extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TREEFOLK);

    public FangornTreeShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(10);

        // Treefolk you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever one or more Treefolk you control attack, add twice that much {G}.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new FangornTreeShepherdEffect(), 1, filter
        ).setTriggerPhrase("Whenever one or more Treefolk you control attack, "));

        // You don't lose unspent green mana as steps and phases end.
        this.addAbility(new SimpleStaticAbility(new YouDontLoseManaEffect(ManaType.GREEN)));
    }

    private FangornTreeShepherd(final FangornTreeShepherd card) {
        super(card);
    }

    @Override
    public FangornTreeShepherd copy() {
        return new FangornTreeShepherd(this);
    }
}

class FangornTreeShepherdEffect extends OneShotEffect {

    FangornTreeShepherdEffect() {
        super(Outcome.Benefit);
        staticText = "add twice that much {G}";
    }

    private FangornTreeShepherdEffect(final FangornTreeShepherdEffect effect) {
        super(effect);
    }

    @Override
    public FangornTreeShepherdEffect copy() {
        return new FangornTreeShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int amount = (Integer) getValue("attackers");
        if (player != null && amount > 0) {
            player.getManaPool().addMana(Mana.GreenMana(2 * amount), game, source);
            return true;
        }
        return false;
    }
}
