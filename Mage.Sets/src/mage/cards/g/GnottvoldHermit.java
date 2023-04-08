package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class GnottvoldHermit extends CardImpl {

    public GnottvoldHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);this.secondSideCardClazz=mage.cards.c.ChromeHostHulk.class;

        // {5}{U/P}: Transform Gnottvold Hermit. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{U/P}")));
    }

    private GnottvoldHermit(final GnottvoldHermit card) {
        super(card);
    }

    @Override
    public GnottvoldHermit copy() {
        return new GnottvoldHermit(this);
    }
}
