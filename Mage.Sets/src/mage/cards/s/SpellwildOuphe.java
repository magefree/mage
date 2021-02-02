package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class SpellwildOuphe extends CardImpl {

    public SpellwildOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Spells that target Spellwild Ouphe cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(-2, new FilterCard("Spells"), TargetController.ANY))
        );
    }

    private SpellwildOuphe(final SpellwildOuphe card) {
        super(card);
    }

    @Override
    public SpellwildOuphe copy() {
        return new SpellwildOuphe(this);
    }
}