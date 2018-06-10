package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class MarblePriest extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WALL, "Walls");

    public MarblePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Walls able to block Marble Priest do so.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new MustBeBlockedByAllSourceEffect(
                        Duration.WhileOnBattlefield,
                        filter
                )
        ));

        // Prevent all combat damage that would be dealt to Marble Priest by Walls.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new MarblePriestPreventionEffect()
        ));
    }

    public MarblePriest(final MarblePriest card) {
        super(card);
    }

    @Override
    public MarblePriest copy() {
        return new MarblePriest(this);
    }
}

class MarblePriestPreventionEffect extends PreventAllDamageToSourceEffect {

    public MarblePriestPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all combat damage that would be dealt to {this} by Walls";
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getFlag()
                && game.getObject(event.getSourceId()).hasSubtype(SubType.WALL, game)
                && event.getTargetId().equals(source.getSourceId());
    }
}
