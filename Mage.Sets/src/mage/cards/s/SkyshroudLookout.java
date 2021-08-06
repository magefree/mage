package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyshroudLookout extends CardImpl {

    public SkyshroudLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Skyshroud Lookout enters the battlefield, seek an Elf card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SkyshroudLookoutEffect()));
    }

    private SkyshroudLookout(final SkyshroudLookout card) {
        super(card);
    }

    @Override
    public SkyshroudLookout copy() {
        return new SkyshroudLookout(this);
    }
}

class SkyshroudLookoutEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    SkyshroudLookoutEffect() {
        super(Outcome.Benefit);
        staticText = "seek an Elf card";
    }

    private SkyshroudLookoutEffect(final SkyshroudLookoutEffect effect) {
        super(effect);
    }

    @Override
    public SkyshroudLookoutEffect copy() {
        return new SkyshroudLookoutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.seekCard(filter, source, game);
    }
}
