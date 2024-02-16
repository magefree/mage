package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
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
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAllSourceEffect(Duration.WhileOnBattlefield, filter)));

        // Prevent all combat damage that would be dealt to Marble Priest by Walls.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(filter, true)));
    }

    private MarblePriest(final MarblePriest card) {
        super(card);
    }

    @Override
    public MarblePriest copy() {
        return new MarblePriest(this);
    }
}
