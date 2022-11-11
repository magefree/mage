package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterMana;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallajiWayfarer extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored spells");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public FallajiWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Fallaji Wayfarer is all colors. This ability doesn't affect its color identity.
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("{this} is all colors. This ability doesn't affect its color identity")
        ));

        // Multicolored spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));
    }

    private FallajiWayfarer(final FallajiWayfarer card) {
        super(card);
    }

    @Override
    public FallajiWayfarer copy() {
        return new FallajiWayfarer(this);
    }

    @Override
    public FilterMana getColorIdentity() {
        FilterMana filterMana = new FilterMana();
        filterMana.setGreen(true);
        return filterMana;
    }
}
