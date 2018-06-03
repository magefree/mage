
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author Loki
 */
public final class PhyrexianMetamorph extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public PhyrexianMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U/P}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        ApplyToPermanent phyrexianMetamorphApplier = new ApplyToPermanent() {
            @Override
            public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
                return apply(game, (MageObject) permanent, source, copyToObjectId);
            }

            @Override
            public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
                if (!mageObject.isArtifact()) {
                    mageObject.addCardType(CardType.ARTIFACT);
                }
                return true;
            }

        };

        // {U/P} ( can be paid with either {U} or 2 life.)
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        Effect effect = new CopyPermanentEffect(filter, phyrexianMetamorphApplier);
        effect.setText("You may have {this} enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types");
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EntersBattlefieldEffect(effect, "", true));
        this.addAbility(ability);
    }

    public PhyrexianMetamorph(final PhyrexianMetamorph card) {
        super(card);
    }

    @Override
    public PhyrexianMetamorph copy() {
        return new PhyrexianMetamorph(this);
    }

}
