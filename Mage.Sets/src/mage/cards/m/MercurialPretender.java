package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.util.functions.AbilityCopyApplier;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MercurialPretender extends CardImpl {

    private static final String effectText = "as a copy of a creature you control, except it has \"{2}{U}{U}: Return this creature to its owner's hand.\"";

    public MercurialPretender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Mercurial Pretender enter the battlefield as a copy of any creature you control,
        // except it has "{2}{U}{U}: Return this creature to its owner's hand."
        Effect effect = new CopyPermanentEffect(new FilterControlledCreaturePermanent(),
                new AbilityCopyApplier(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{2}{U}{U}"))));
        effect.setText(effectText);
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    private MercurialPretender(final MercurialPretender card) {
        super(card);
    }

    @Override
    public MercurialPretender copy() {
        return new MercurialPretender(this);
    }
}
