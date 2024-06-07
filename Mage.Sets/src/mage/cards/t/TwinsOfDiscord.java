package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author grimreap124
 */
public final class TwinsOfDiscord extends CardImpl {

    private static final FilterControlledCreaturePermanent colorlessCreatureFilter = new FilterControlledCreaturePermanent("colorless creature you control");

    static {
        colorlessCreatureFilter.add(ColorlessPredicate.instance);
    }

    public TwinsOfDiscord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");
        
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever you attack, choose odd or even. Creatures with mana value of that quality can't block this turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new TwinsOfDiscordEffect(), 1));
        
        // Each other colorless creature you control has bloodthirst 2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
            new BloodthirstAbility(2),
                Duration.WhileOnBattlefield,
                colorlessCreatureFilter, true).setText("Each other colorless creature you control has bloodthirst 2")));
    }

    private TwinsOfDiscord(final TwinsOfDiscord card) {
        super(card);
    }

    @Override
    public TwinsOfDiscord copy() {
        return new TwinsOfDiscord(this);
    }
}

class TwinsOfDiscordEffect extends OneShotEffect {

    private static final FilterCreaturePermanent evenFilter = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent oddFilter = new FilterCreaturePermanent();

    static {
        evenFilter.add(ManaValueParityPredicate.EVEN);
        oddFilter.add(ManaValueParityPredicate.ODD);
    }

    TwinsOfDiscordEffect() {
        super(Outcome.Benefit);
        staticText = "choose odd or even. Creatures with mana value of that quality can't block this turn.";
    }

    private TwinsOfDiscordEffect(final TwinsOfDiscordEffect effect) {
        super(effect);
    }

    @Override
    public TwinsOfDiscordEffect copy() {
        return new TwinsOfDiscordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = player.chooseUse(
                outcome, "Odd or even?", null,
                "Odd", "Even", source, game
        ) ? oddFilter : evenFilter;
        if (filter == null) {
            return false;
        }
        
        game.addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn), source);
        return true;
    }
}
