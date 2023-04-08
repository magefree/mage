package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChromeHostSeedshark extends CardImpl {

    public ChromeHostSeedshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHARK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, incubate X, where X is that spell's mana value.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ChromeHostSeedsharkEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private ChromeHostSeedshark(final ChromeHostSeedshark card) {
        super(card);
    }

    @Override
    public ChromeHostSeedshark copy() {
        return new ChromeHostSeedshark(this);
    }
}

class ChromeHostSeedsharkEffect extends OneShotEffect {

    ChromeHostSeedsharkEffect() {
        super(Outcome.Benefit);
        staticText = "incubate X, where X is that spell's mana value";
    }

    private ChromeHostSeedsharkEffect(final ChromeHostSeedsharkEffect effect) {
        super(effect);
    }

    @Override
    public ChromeHostSeedsharkEffect copy() {
        return new ChromeHostSeedsharkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        return spell != null && new IncubateEffect(spell.getManaValue()).apply(game, source);
    }
}
