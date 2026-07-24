package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author nandmp
 */
public final class CaptainMarVellSpaceBorn extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("spells");

    public CaptainMarVellSpaceBorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Cosmic Awareness -- As long as an opponent has cast a spell this turn, you may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter),
                OpponentCastSpellThisTurnCondition.instance
        ).setText("as long as an opponent has cast a spell this turn, "
                + "you may cast spells as though they had flash"))
                .withFlavorWord("Cosmic Awareness"));
    }

    private CaptainMarVellSpaceBorn(final CaptainMarVellSpaceBorn card) {
        super(card);
    }

    @Override
    public CaptainMarVellSpaceBorn copy() {
        return new CaptainMarVellSpaceBorn(this);
    }
}

enum OpponentCastSpellThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null
                && game.getOpponents(source.getControllerId()).stream().anyMatch(playerId -> watcher.getCount(playerId) > 0);
    }
}
