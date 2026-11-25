package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Grath
 */
public final class EvercoatUrsine extends CardImpl {

    public EvercoatUrsine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Hideaway 3
        this.addAbility(new HideawayAbility(this, 3));

        // Hideaway 3
        this.addAbility(new HideawayAbility(this, 3));

        // Whenever Evercoat Ursine deals combat damage to a player, if there are cards exiled with it, you may play
        // one of them without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HideawayPlayEffect(true))
                .withInterveningIf(EvercoatUrsineCondition.instance));
    }

    private EvercoatUrsine(final EvercoatUrsine card) {
        super(card);
    }

    @Override
    public EvercoatUrsine copy() {
        return new EvercoatUrsine(this);
    }
}

/**
 * "if there are cards exiled with it" is an intervening-if (CR 603.4): with nothing exiled with the
 * permanent the ability must not trigger at all. Reads the zone HideawayPlayEffect reads at resolution.
 */
enum EvercoatUrsineCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // the trigger-check ability has no stack-moment zcc yet (0): read the permanent's own
        int zcc = source.getStackMomentSourceZCC();
        if (zcc == 0) {
            Permanent permanent = source.getSourcePermanentOrLKI(game);
            if (permanent == null) {
                return false;
            }
            zcc = permanent.getZoneChangeCounter(game);
        }
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zcc));
        return exile != null && !exile.isEmpty();
    }

    @Override
    public String toString() {
        return "there are cards exiled with it";
    }
}
