package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GleamingOverseer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombie tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public GleamingOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Gleaming Overseer enters the battlefield, amass 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(1, SubType.ZOMBIE)));

        // Zombie tokens you control have hexproof and menace.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(), Duration.WhileOnBattlefield, filter
        ).setText("and menace"));
        this.addAbility(ability);
    }

    private GleamingOverseer(final GleamingOverseer card) {
        super(card);
    }

    @Override
    public GleamingOverseer copy() {
        return new GleamingOverseer(this);
    }
}
