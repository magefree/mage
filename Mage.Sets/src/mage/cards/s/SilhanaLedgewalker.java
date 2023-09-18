package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SilhanaLedgewalker extends CardImpl {

    private static final FilterCreaturePermanent onlyFlyingCreatures = new FilterCreaturePermanent("except by creatures with flying");

    static {
        onlyFlyingCreatures.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SilhanaLedgewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Hexproof (This creature can't be the target of spells or abilities your opponents control.)
        this.addAbility(HexproofAbility.getInstance());

        // Silhana Ledgewalker can't be blocked except by creatures with flying.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(onlyFlyingCreatures, Duration.WhileOnBattlefield)));
    }

    private SilhanaLedgewalker(final SilhanaLedgewalker card) {
        super(card);
    }

    @Override
    public SilhanaLedgewalker copy() {
        return new SilhanaLedgewalker(this);
    }
}
