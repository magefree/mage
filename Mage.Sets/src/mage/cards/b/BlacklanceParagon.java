package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Tsirides
 */
public final class BlacklanceParagon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KNIGHT);

    public BlacklanceParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Blacklance Paragon enters the battlefield, target Knight gains deathtouch and lifelink until end of turn. 
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("target Knight gains deathtouch"));
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and lifelink until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private BlacklanceParagon(final BlacklanceParagon card) {
        super(card);
    }

    @Override
    public BlacklanceParagon copy() {
        return new BlacklanceParagon(this);
    }
}
