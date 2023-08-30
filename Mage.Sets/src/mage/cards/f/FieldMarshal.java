package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author anonymous
 */
public final class FieldMarshal extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldier creatures");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public FieldMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability =  new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        ability.addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)
                .setText("and have first strike"));
        this.addAbility(ability);
    }

    private FieldMarshal(final FieldMarshal card) {
        super(card);
    }

    @Override
    public FieldMarshal copy() {
        return new FieldMarshal(this);
    }
}
