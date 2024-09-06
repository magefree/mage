package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfImpostors extends CardImpl {

    public FearOfImpostors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Fear of Impostors enters, counter target spell. Its controller manifests dread.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FearOfImpostorsEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private FearOfImpostors(final FearOfImpostors card) {
        super(card);
    }

    @Override
    public FearOfImpostors copy() {
        return new FearOfImpostors(this);
    }
}

class FearOfImpostorsEffect extends OneShotEffect {

    FearOfImpostorsEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. Its controller manifests dread";
    }

    private FearOfImpostorsEffect(final FearOfImpostorsEffect effect) {
        super(effect);
    }

    @Override
    public FearOfImpostorsEffect copy() {
        return new FearOfImpostorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID spellId = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(game.getControllerId(spellId));
        game.getStack().counter(spellId, source, game);
        game.processAction();
        return player != null && ManifestDreadEffect.doManifestDread(player, source, game) != null;
    }
}
