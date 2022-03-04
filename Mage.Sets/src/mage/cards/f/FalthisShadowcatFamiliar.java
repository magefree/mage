package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalthisShadowcatFamiliar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Commanders");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public FalthisShadowcatFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Commanders you control have menace and deathtouch.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and deathtouch"));
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private FalthisShadowcatFamiliar(final FalthisShadowcatFamiliar card) {
        super(card);
    }

    @Override
    public FalthisShadowcatFamiliar copy() {
        return new FalthisShadowcatFamiliar(this);
    }
}
